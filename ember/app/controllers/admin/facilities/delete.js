import Ember from 'ember';

const {
  inject: {
    service,
  },
} = Ember;

export default Ember.Controller.extend({
  ajax: service('ajax'),

  actions: {
    deleteFacility() {
      this.get('ajax').del('/admin/deleteFacility/' + this.get('model.facility.id'), {
        xhrFields: {
          withCredentials: true,
        },
      })
      .then(
        () => this.transitionToRoute('admin.facilities'),
        () => alert('You can not delete a Facility that is a assinged to a Lodging. Please remove if from all lodgings before proceeding'));
    },
  },
});
