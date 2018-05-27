import Ember from 'ember';

const {
  inject: {
    service,
  },
} = Ember;

export default Ember.Route.extend({
  ajax: service('ajax'),

  model() {
    return Ember.RSVP.hash({
      popularLodgings: this.get('ajax').request('/getPopularLodgings'),
      popularLocations: this.get('ajax').request('/getPopularLocations'),
      numberOfRastaurants: this.get('ajax').request('/getNumberOfLodgings'),
      user: this.get('ajax').request('/getCurrentUser', {
        xhrFields: {
          withCredentials: true,
        },
      }),
    });
  },
});
