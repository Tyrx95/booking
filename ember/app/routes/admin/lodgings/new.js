import Ember from 'ember';

export default Ember.Route.extend({
  ajax: Ember.inject.service(),
  model() {
    return Ember.RSVP.hash({
      lodging: {},
      cities: this.get('ajax').request('/getAllCities'),
      facilities: this.get('ajax').request('/getAllFacilities'),
    });
  },

  afterModel(model) {
    model.lodging.city = model.cities[0];
    model.lodging.priceRange = 1;
    model.lodging.facilities = [];
    model.lodging.rooms = [];
    model.lodging.photos = [];
  },
});
