import Ember from 'ember';

export default Ember.Route.extend({
  ajax: Ember.inject.service(),
  model(params) {
    return Ember.RSVP.hash({
      lodging: this.get('ajax').request('/getLodging/' + params.lodging_id),
    });
  },
});
