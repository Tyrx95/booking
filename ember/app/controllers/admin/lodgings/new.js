import Ember from 'ember';
import $ from 'jquery';

const {
  inject: {
    service,
  },
  computed,
  String: {
    htmlSafe,
  },
} = Ember;

export default Ember.Controller.extend({
  ajax: service('ajax'),

  uploadProgressProfile: null,
  uploadProgressCover: null,
  uploadProgressGallery: null,
  trueClass: 'full',
  falseClass: 'empty',

  profileImageStyle: computed('model.lodging.profileImagePath', function () {
    return htmlSafe('background-image: url(' + this.get('model.lodging.profileImagePath') + ')');
  }),

  coverImageStyle: computed('model.lodging.coverImagePath', function () {
    return htmlSafe('background-image: url(' + this.get('model.lodging.coverImagePath') + ')');
  }),

  isRangeOne: computed('model.lodging.priceRange', function () {
    return this.get('model.lodging.priceRange') >= 1 ? this.get('trueClass') : this.get('falseClass');
  }),

  isRangeTwo: computed('model.lodging.priceRange', function () {
    return this.get('model.lodging.priceRange') >= 2 ? this.get('trueClass') : this.get('falseClass');
  }),

  isRangeThree: computed('model.lodging.priceRange', function () {
    return this.get('model.lodging.priceRange') >= 3 ? this.get('trueClass') : this.get('falseClass');
  }),

  isRangeFour: computed('model.lodging.priceRange', function () {
    return this.get('model.lodging.priceRange') >= 4 ? this.get('trueClass') : this.get('falseClass');
  }),

  facilityPrimitives: computed('model.facilities', function () {
    return this.get('model.facilities').map((facility) => facility.name);
  }),

  selectedFacilityPrimitives: computed('model.lodging.facilities', function () {
    return this.get('model.lodging.facilities').map((facility) => facility.name);
  }),

  actions: {

    removePhoto(photo) {
      this.get('ajax').del('/admin/deletePicture/' + photo.id, {
        xhrFields: {
          withCredentials: true,
        },
      }).then(() => this.get('model.lodging.photos').removeObject(photo));
    },

    addRoom() {
      this.get('model.lodging.rooms').pushObject({ id: null, lodgingId: this.get('model.lodging.id'), numberOfRooms: 0 });
    },

    removeRoom(roomId) {
      this.get('model.lodging.rooms').removeObject(this.get('model.lodging.rooms').findBy('id', roomId));
    },

    setCity() {
      let selectBox = document.getElementById('city-select');
      this.set('model.lodging.city', this.get('model.cities').findBy('id', selectBox.options[selectBox.selectedIndex].value));
    },

    submitLodging() {
      this.set('model.lodging.latitude', this.get('marker').getPosition().lat());
      this.set('model.lodging.longitude', this.get('marker').getPosition().lng());

      if (this.get('marker').getPosition() === this.get('defaultMerkerPosition')) {
        return alert('Please Move the Map Marker to the correct position');
      }

      let markerLatLng = new google.maps.LatLng(this.get('model.lodging.latitude'), this.get('model.lodging.longitude'));
      if (!google.maps.geometry.poly.containsLocation(markerLatLng, this.get('polygon'))) {
        return alert('Marker out of Bounds. Please position the marker within the selected City.');
      }

      if (this.get('uploadProgressProfile') !== null || this.get('uploadProgressCover') !== null) {
        return alert('Please wait for upload to finish');
      }

      let selectedFacilities = this.get('selectedFacilityPrimitives').map((primitive) => this.get('model.facilities').find((facility) => facility.name === primitive));

      this.set('model.lodging.areaInfo', JSON.stringify(this.get('model.lodging.areaInfo')));
      this.set('model.lodging.facilities', selectedFacilities);
      let jsonData = JSON.stringify(this.get('model.lodging'));

      let isNew = typeof this.get('model.lodging.id') === 'undefined';
      let method = isNew ? 'post' : 'put';
      let request = '/admin/' + (isNew ? 'createLodging' : 'editLodging');

      this.get('ajax')[method](request, {
        xhrFields: {
          withCredentials: true,
        },
        contentType: 'application/json',
        data: jsonData,
      })
      .then((response) =>  {
        if(!isNew){
          return this.transitionToRoute('admin.lodgings');
        }
        else{
          console.log(response);
          this.send('uploadImagesOnCreate',response);
          return this.transitionToRoute('admin.lodgings');
        }
      });
    },

    priceRangeChange(value) {
      $('#pricerange-control input').each((index, element) => {
        if (element.value === 0) { return; }

        $(element).parent().css('color', element.value <= value ? '#40e0d0' : 'rgba(19, 29, 36, 0.2)');
      });
    },

    repositionLodgingMarker(newLat, newLong) {
      this.set('model.lodging.latitude', newLat);
      this.set('model.lodging.longitude', newLong);
    },

    uploadImagesOnCreate(lodgingId){
      if(this.get('model.lodging.photos').length > 0) {
        var photosArr = this.get('model.lodging.photos').toArray();
        photosArr.forEach(function(photo){
          photo.lodgingId = lodgingId;
        })
        this.get('ajax').patch('/admin/updatePictures', {
          xhrFields: {
            withCredentials: true,
          },
          data: JSON.stringify(photosArr),
        });
      }
    },

    uploadedImage(imageFor, fileExtension, timestamp) {
      this.get('ajax').patch('/admin/updatePicture', {
        xhrFields: {
          withCredentials: true,
        },
        data: JSON.stringify({
          lodgingId: this.get('model.lodging.id'),
          timestamp: timestamp,
          imageType: imageFor,
          extension: fileExtension,
        }),
      })
      .then((response) => this.set(response.imageFor === 'profile' ? 'model.lodging.profileImagePath' : 'model.lodging.coverImagePath', response.url));
    },

    uploadedGalleryImage(imageFor, fileExtension, timestamp) {
      this.get('ajax').patch('/admin/updatePicture', {
        xhrFields: {
          withCredentials: true,
        },
        data: JSON.stringify({
          lodgingId: this.get('model.lodging.id'),
          imageType: imageFor,
          extension: fileExtension,
          timestamp: timestamp,
        }),
      })
        .then((response) => {
          this.get('model.lodging.photos').pushObject(JSON.parse(response));

        });
    },

    addAreaInfoLandmarks() {
      let areaInfo = this.get('model.lodging.areaInfo.landmarks');
      if (typeof areaInfo === 'undefined') {
        this.set('model.lodging.areaInfo', {});
        this.set('model.lodging.areaInfo.landmarks', []);
        this.set('model.lodging.areaInfo.markets', []);
        this.set('model.lodging.areaInfo.airports', []);
      }

      this.get('model.lodging.areaInfo.landmarks').pushObject({ id: null, name: '', description: '', price: 0 });
    },

    addAreaInfoMarket() {
      let areaInfo = this.get('model.lodging.areaInfo.markets');
      if (typeof areaInfo === 'undefined') {
        this.set('model.lodging.areaInfo', {});
        this.set('model.lodging.areaInfo.landmarks', []);
        this.set('model.lodging.areaInfo.markets', []);
        this.set('model.lodging.areaInfo.airports', []);
      }

      this.get('model.lodging.areaInfo.markets').pushObject({ id: null, name: '', description: '', price: 0 });
    },

    addAreaInfoAirports() {
      let areaInfo = this.get('model.lodging.areaInfo.airports');
      if (typeof areaInfo === 'undefined') {
        this.set('model.lodging.areaInfo', {});
        this.set('model.lodging.areaInfo.landmarks', []);
        this.set('model.lodging.areaInfo.markets', []);
        this.set('model.lodging.areaInfo.airports', []);
      }

      this.get('model.lodging.areaInfo.airports').pushObject({ id: null, name: '', description: '', price: 0 });
    },

    removeLandmarksItem(item) {
      this.get('model.lodging.areaInfo.landmarks').removeObject(item);
    },

    removeMarketItem(item) {
      this.get('model.lodging.areaInfo.markets').removeObject(item);
    },

    removeAirportsItem(item) {
      this.get('model.lodging.areaInfo.airports').removeObject(item);
    },
  },

});
