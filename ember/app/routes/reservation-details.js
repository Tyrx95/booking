import Ember from 'ember';

const {
  inject: {
    service,
  },
} = Ember;

export default Ember.Route.extend({
  ajax: service('ajax'),

  model(params) {
    return Ember.RSVP.hash({
      reservation: this.get('ajax').request('/getReservation/' + params.reservation_id),
      cities: this.get('ajax').request('/getAllCities'),
      user: this.get('ajax').request('/getCurrentUser', {
        xhrFields: {
          withCredentials: true,
        },
      }),
    });
  },

  afterModel(model, transition) {
    transition.send('lodging', model.reservation.room.lodgingId);
  },

  actions: {
    lodging: function (lodgingId) {
        this.get('ajax').request('/getLodging/' + lodgingId)
        .then((repsonse) => this.controllerFor('reservation-details').set('lodging', repsonse));
      },
  },
});
