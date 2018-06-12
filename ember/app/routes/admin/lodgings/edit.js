import Ember from 'ember';

export default Ember.Route.extend({
  ajax: Ember.inject.service(),
  model(params) {
    return Ember.RSVP.hash({
      lodging: this.get('ajax').request('/getLodging/' + params.lodging_id),
      cities: this.get('ajax').request('/getAllCities'),
      facilities: this.get('ajax').request('/getAllFacilities'),
      isEdit: true,
    });
  },

  renderTemplate: function (controller, model) {
   // model.lodging.areaInfo = JSON.parse(model.lodging.areaInfo);
    this.render('admin.lodgings.new', {
        model: model,
      });
  },
});
