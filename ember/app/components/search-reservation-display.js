import Ember from 'ember';

const {
  inject: {
    service,
  },
  computed,
} = Ember;

export default Ember.Component.extend({
  ajax: service('ajax'),

  inquiryResponse: computed('id', 'params', function () {
    this.get('ajax').post('/postReservationInquiry', {
      data: JSON.stringify({
        lodgingId: this.get('id'),
        numberOfPeople: this.get('params').numberOfPeople,
        date: this.get('params').date,
        endDate: this.get('params').endDate,
      }),
    })
    .then(
      (response) => this.set('inquiryResponse', response),
      (error) => {
        this.set('hasError', true);
        this.set('errorMessage', error.errors[0].title);
      }
    );
  }),

  actions: {
    reserve(startDate, endDate) {
      this.sendAction('onReserve', this.get('id'), this.get('params').numberOfPeople, startDate, endDate);
    },
  },
});
