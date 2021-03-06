import Ember from 'ember';

export default Ember.Route.extend({
  ajax: Ember.inject.service(),
  model(params) {
    return Ember.RSVP.hash({
      facility: this.get('ajax').request('/getFacility/' + params.facility_id),
    });
  },
});
