import Ember from 'ember';
import config from './config/environment';

const Router = Ember.Router.extend({
  location: config.locationType,
  rootURL: config.rootURL,
});

Router.map(function () {
  this.route('login');
  this.route('register');
  this.route('lodgings', function () {});

  this.route('user', function () {
    this.route('settings');
  });

  this.route('lodging', { path: 'lodging/:lodging_id' });

  this.route('badRequest', { path: '*path' });
  this.route('search-results');
  this.route('admin', function () {
    this.route('lodgings', function () {
      this.route('new');
      this.route('edit', { path: 'edit/:lodging_id' });
      this.route('delete', { path: 'delete/:lodging_id' });
      this.route('reservations', { path: 'reservations/:lodging_id' });
    });

    this.route('locations', function () {
      this.route('new');
      this.route('edit', { path: 'edit/:location_id' });
      this.route('delete', { path: 'delete/:location_id' });
    });

    this.route('users', function () {
      this.route('new');
      this.route('edit', { path: 'edit/:user_id' });
      this.route('delete', { path: 'delete/:user_id' });
    });

    this.route('settings');
    this.route('facilities', function () {
      this.route('new');
      this.route('edit', { path: 'edit/:facility_id' });
      this.route('delete', { path: 'delete/:facility_id' });
    });
  });

  this.route('reservation-details', { path: 'reservation-details/:reservation_id' });
});

export default Router;
