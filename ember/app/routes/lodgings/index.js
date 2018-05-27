import Ember from 'ember';
import $ from 'jquery';

export default Ember.Route.extend({
  ajax: Ember.inject.service(),
  queryParams: {
    currentPage: {
      refreshModel: true,
    },
    nameFilter: {
      refreshModel: true,
    },
    priceFilter: {
      refreshModel: true,
    },
    ratingFilter: {
      refreshModel: true,
    },
    facilityFilter: {
      refreshModel: true,
    },
    sortBy: {
      refreshModel: true,
    },
    city: {
      refreshModel: true,
    },
  },
  model(params) {
    return Ember.RSVP.hash({
      response: this.get('ajax').request('/getAllLodgings?pageNumber=' + params.currentPage + '&pageSize=9&nameFilter=' + params.nameFilter + '&priceFilter=' + params.priceFilter + '&ratingFilter=' + params.ratingFilter + '&sortBy=' + params.sortBy + '&facilityFilter=' + params.facilityFilter + '&cityFilter=' + params.city),
      popularLocations: this.get('ajax').request('/getPopularLocations'),
      facilities: this.get('ajax').request('/getAllFacilitiesAsString'),
      user: this.get('ajax').request('/getCurrentUser', {
        xhrFields: {
          withCredentials: true,
        },
      }),
    });

  },
});
