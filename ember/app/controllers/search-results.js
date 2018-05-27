import Ember from 'ember';

const {
  inject: {
    service,
  },
} = Ember;

export default Ember.Controller.extend({
  ajax: service('ajax'),

  queryParams: {
    lodgingName: 'name',
    numberOfPeople: 'people',
    date: 'date',
    endDate: 'endDate',
    currentPage: 'page',
  },
  lodgingName: '',
  numberOfPeople: 0,
  currentPage: 1,

  actions: {
    reserve(lodgingId, numberOfPeople, startDate, endDate) {
      this.get('ajax').post('/postReservation', {
        contentType: 'application/json',
        data: JSON.stringify({
          lodgingId: lodgingId,
          numberOfPeople: numberOfPeople,
          date: new Date(startDate).toISOString().slice(0, 10),
          endDate: new Date(endDate).toISOString().slice(0, 10),
        }),
      })
      .then((response) => this.transitionToRoute('reservation-details', response.id));
    },
  },
});
