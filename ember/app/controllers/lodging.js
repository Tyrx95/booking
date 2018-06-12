import Ember from 'ember';
import $ from 'jquery';

const {
  inject: {
    service,
  },
  computed,
  computed: {
    alias,
    gt,
  },
} = Ember;

export default Ember.Controller.extend({
  ajax: service('ajax'),

  numberOfPeople: 1,
  review_text: '',
  review_score: 0,
  seeMorePhotos: false,


  areAvailableRooms: gt('model.response.numberOfRoomsLeft', 0),

  /*
  landmarksAreaInfo: computed('model.lodging.areaInfo', function () {
    if(this.get('model.lodging.areaInfo') !== undefined
      && this.get('model.lodging.areaInfo') !== 'null' && this.get('model.lodging.areaInfo') !== null){
      return JSON.parse(this.get('model.lodging.areaInfo')).landmarks;
    }
  }),

  marketsAreaInfo: computed('model.lodging.areaInfo', function () {
    if(this.get('model.lodging.areaInfo') !== undefined
      && this.get('model.lodging.areaInfo') !== 'null' && this.get('model.lodging.areaInfo') !== null){
      return JSON.parse(this.get('model.lodging.areaInfo')).markets;
    }
  }),

  airportsAreaInfo: computed('model.lodging.areaInfo', function () {
    if(this.get('model.lodging.areaInfo') !== undefined
      && this.get('model.lodging.areaInfo') !== 'null' && this.get('model.lodging.areaInfo') !== null){
      return JSON.parse(this.get('model.lodging.areaInfo')).airports;
    }
  }),*/

  photos: computed('model.lodging.photos', function () {
    let photos = [];
    this.get('model.lodging.photos').forEach(function (photo) {
      photos.pushObject({
        src: photo.path,
        w: 1024,
        h: 768,
        title: 'Lodging image'
      })
    })
    return photos;
  }),

  hasMap: computed('model.lodging.latitude', 'model.lodging.longitude', function () {
    return this.get('model.lodging.latitude') !== 0 && this.get('model.lodging.longitude') !== 0;
  }),

  date: new Date().toISOString().substring(0, 10),
  endDate: new Date().toISOString().substring(0, 10),
  todayDate:  new Date().toISOString().substring(0, 10),
  minEndDate: computed('date', function(){
    //return new Date(this.stringToDate(this.get('date'), 'yyyy-mm-dd','-').getDate()+1).toISOString().substring(0, 10);
    var date = this.stringToDate(this.get('date'), 'yyyy-mm-dd','-');
    date.setDate(date.getDate()+2);
    return date.toISOString().substring(0, 10);
  }),
  stringToDate: function(_date,_format,_delimiter)
  {
    var formatLowerCase=_format.toLowerCase();
    var formatItems=formatLowerCase.split(_delimiter);
    var dateItems=_date.split(_delimiter);
    var monthIndex=formatItems.indexOf("mm");
    var dayIndex=formatItems.indexOf("dd");
    var yearIndex=formatItems.indexOf("yyyy");
    var month=parseInt(dateItems[monthIndex]);
    month-=1;
    var formatedDate = new Date(dateItems[yearIndex],month,dateItems[dayIndex]);
    return formatedDate;
  },

  actions: {
    showRatingDialog() {
      let myReview = this.get('model.lodging.reviews').find((element) => element.userId === this.get('model.user.object.id'));

      this.set('review_score', myReview.rating);
      this.set('review_text', myReview.review);
      this.send('ratingChanged', this.get('review_score'));
    },

    submitReviewAction() {
      this.get('ajax').post('/postReview', {
        xhrFields: {
          withCredentials: true,
        },
        data: JSON.stringify({
          lodgingId: this.get('model.lodging.id'),
          reviewScore: this.get('review_score'),
          reviewText: this.get('review_text'),
        }),
      })
      .then(
        () => {
          $('#submitRatingModal').modal('hide');
          this.get('ajax').request('/getLodging/' + this.get('model.lodging.id')).then((response) => this.set('model.lodging', response));
        }, (error) => {
          this.set('model.hasError', true);
          this.set('model.errorMessage', error);
        }
      );
    },

    findRoom() {
      var data = JSON.stringify({
        lodgingId: this.get('model.lodging.id'),
        numberOfPeople: this.get('numberOfPeople'),
        date: this.get('date'),
        endDate: this.get('endDate'),
      });
      this.get('ajax').post('/postReservationInquiry', {
        data: data,
      })
      .then(
        (response) => {
          this.set('model.didFindRoom', true);
          this.set('model.response', response);
        }, (error) => alert(error)
      );
    },

    reserve(suggestion) {

      let date = new Date(suggestion.startDate);
      date.setDate(date.getDate()+1);
      let endDate = new Date(suggestion.endDate);
      endDate.setDate(endDate.getDate()+1);
      date = date.toISOString().substring(0, 10);
      endDate= endDate.toISOString().substring(0, 10);
      this.get('ajax').post('/postReservation', {
        contentType: 'application/json',
        data: JSON.stringify({
          lodgingId: this.get('model.response.inquiry.lodgingId'),
          numberOfPeople: this.get('model.response.inquiry.numberOfPeople'),
          date: date,
          endDate: endDate
        }),
      })
      .then((response) => this.transitionToRoute('reservation-details', response.id));
    },

    setNumberOfPeople() {
      let selectBox = document.getElementById('numberOfPeople');
      this.set('numberOfPeople', selectBox.options[selectBox.selectedIndex].value);
    },

    ratingChanged(value) {
      $('#rating-control input').each((index, element) => $(element).parent().css('color', element.value < value ? '#40e0d0' : 'rgba(19, 29, 36, 0.2)'));
    },
    toggleSeeMore(){
      this.toggleProperty('seeMorePhotos');
    }
  },

});
