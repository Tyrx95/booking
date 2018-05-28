import Ember from 'ember';

const {
  inject: {
    service,
  },
  computed,
  computed: {
    alias,
    not,
  },
} = Ember;

export default Ember.Controller.extend({
  ajax: service('ajax'),

  cityId: '',

  userIsLoggedIn: alias('model.user.isLoggedIn'),

  reservedOn: alias('model.reservation.reservedOn'),
  reservationConfirmed: alias('model.reservation.confirmed'),
  countdownStart: computed('reservedOn', function () { return this.get('reservedOn') + 300000; }),
  countdownEnd: computed('reservedOn', function () { return this.get('reservedOn'); }),

  expirationCounter: computed('countdownStart', 'countdownEnd', function () {
    return new Date(new Date(this.get('countdownStart')).getTime() - new Date(this.get('countdownEnd')).getTime()).getTime();
  }),

  price: computed('model.reservation.startDate','model.reservation.endDate','model.reservation.room.price', function () {
    var millisDiff = Math.abs(this.get('model.reservation.endDate') - this.get('model.reservation.startDate'));
    var diffDays = (millisDiff)/( (1000*60*60*24));
    return diffDays * this.get('model.reservation.room.price') *100;
  }),

  hasExpired: computed('expirationCounter', 'reservationConfirmed', function () {
    return this.get('reservationConfirmed') ? false : this.get('expirationCounter') <= 0;
  }),

  isNotLoggedIn: not('userIsLoggedIn'),
  showRegistrationForm: true,

  actions: {
    setCity() {
      let selectBox = document.getElementById('city-select');
      this.set('cityId', selectBox.options[selectBox.selectedIndex].value);
    },

    confirmReservation() {
      this.set('model.reservation.user', this.get('model.user.object'));
      this.set('reservationConfirmed', true);
      let date = new Date(this.get('model.reservation.startDate'));
      date.setDate(date.getDate()+1);
      let endDate = new Date(this.get('model.reservation.endDate'));
      endDate.setDate(endDate.getDate()+1);
      date = date.toISOString().substring(0, 10);
      endDate= endDate.toISOString().substring(0, 10);
      this.set('model.reservation.startDate', date);
      this.set('model.reservation.endDate', endDate);
      let reservedOn = new Date(this.get('model.reservation.reservedOn'));
      this.set('model.reservation.reservedOn', reservedOn.getTime());
      this.get('ajax').post('/confirmReservation', {
        xhrFields: {
          withCredentials: true,
        },
        contentType: 'application/json',
        data: JSON.stringify({
          reservation: this.get('model.reservation'),
        }),
      })
      .then(
        () => this.transitionToRoute('user'),
        (error) => alert(error)
      );
    },

    authenticate() {
      this.get('ajax').post('/login', {
        xhrFields: {
          withCredentials: true,
        },
        data: JSON.stringify({
          email: this.get('email'),
          password: this.get('password'),
        }),
      })
      .then(
        (response) => {
          this.set('model.user.isLoggedIn', true);
          this.set('model.user.object', response);
        }, (error) => {
          this.set('hasError', true);
          this.set('errorMessage', error.errors[0].title);
        }
      );
    },

    register() {
      this.get('ajax').post('/register', {
        xhrFields: {
          withCredentials: true,
        },
        data: JSON.stringify({
          firstName: this.get('first_name'),
          lastName: this.get('last_name'),
          email: this.get('email'),
          password: this.get('password'),
          address: this.get('address'),
          phone: this.get('phone_number'),
          cityId: this.get('cityId'),
        }),
      })
      .then(
        (response) => {
          this.set('model.user.isLoggedIn', true);
          this.set('model.user.object', response);
        }, (error) =>  console.error(error)
      );
    },

    showLoginForm() {
      this.set('showRegistrationForm', false);
    },

    showRegisterForm() {
      this.set('showRegistrationForm', true);
    },

    processStripeToken({id}){
      this.get('ajax').post('/processStripePayment', {
        xhrFields: {
          withCredentials: true,
        },
        data: id
      })
        .then(
          () => this.send('confirmReservation')
          , (error) =>  console.error(error)
        );
    }
  },
});
