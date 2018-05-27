export default Ember.Component.extend({
  canEditMarker: false,
  canEditBounds: false,

  didInsertElement(...args) {
    this._super(...args);
    this.renderGoogleMap($('.map-canvas')[0]);
  },

  renderGoogleMap(container) {
    let defaultLat=43.846941;
    let defaultLen=18.372303;
    let centerLat,centerLng;
    if((this.get('markerLat') === 0 && this.get('markerLng') === 0) || !this.get('markerLat') ||
      !this.get('markerLng')){
      centerLat=defaultLat
      centerLng=defaultLen;;
    }
    else{
      centerLat=this.get('markerLat');
      centerLng=this.get('markerLng');
    }

    let options = {
      center: new window.google.maps.LatLng(centerLat, centerLng),
      zoom: 12,
    };

    let map = new window.google.maps.Map(container, options);

    let bounds = (typeof this.get('bounds') !== 'undefined') ? JSON.parse(this.get('bounds')) : null;
    let coords = (bounds !== null && bounds.length > 0) ? bounds :
    [
      { lat: 44.773, lng: 17.244 },
      { lat: 43.624, lng: 17.310 },
      { lat: 43.630, lng: 18.904 },
      { lat: 44.773, lng: 18.907 },
    ];

    let markerPosition;
    if((this.get('markerLat') === 0 && this.get('markerLng') === 0) || !this.get('markerLat')  ||
      !this.get('markerLng')) {
      markerPosition= new google.maps.LatLng(defaultLat, defaultLen);
    } else {
      markerPosition = new google.maps.LatLng(this.get('markerLat'), this.get('markerLng'));
    }

    let marker = new google.maps.Marker({
      position: markerPosition,
      draggable: this.get('canEditMarker'),
    });

    marker.setMap(map);
    this.set('marker', marker);

    let polygon = new google.maps.Polygon({
      paths: coords,
      strokeColor: '#40e0d0',
      strokeOpacity: 0.8,
      strokeWeight: 2,
      fillColor: '#40e0d0',
      fillOpacity: 0.1,
      canEdit: this.get('canEditBounds'),
      draggable: this.get('canEditBounds'),
      geodesic: true,
    });
    this.set('polygon', polygon);
  }
});
