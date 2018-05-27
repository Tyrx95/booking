import Ember from 'ember';

const {
  inject: {
    service,
  },
} = Ember;

export default Ember.Route.extend({
  ajax: service('ajax'),

  queryParams: {
    lodgingName: {
      refreshModel: true,
    },
    numberOfPeople: {
      refreshModel: true,
    },
    date: {
      refreshModel: true,
    },
    endDate: {
      refreshModel: true,
    },
    currentPage: {
      refreshModel: true,
    },
  },

  model(params) {
    return Ember.RSVP.hash({
      params: params,
      lodgings: this.get('ajax').request('/getAllLodgings?pageNumber=' + params.currentPage + '&pageSize=9&nameFilter=' + params.lodgingName),
      user: this.get('ajax').request('/getCurrentUser', {
        xhrFields: {
          withCredentials: true,
        },
      }),
    });
  },
});
