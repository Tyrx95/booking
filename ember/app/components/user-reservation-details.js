import Ember from 'ember';

const {
  inject: {
    service,
  },
  computed,
} = Ember;

export default Ember.Component.extend({
  ajax: service('ajax'),

  lodging: computed('lodging', function () {
    return this.get('ajax').request('/getLodging/' + this.get('reservation.room.lodgingId')).then((response) => this.set('lodging', response));
  }),
});
