import Ember from 'ember';

const {
  inject: {
    service,
  },
} = Ember;

export default Ember.Controller.extend({
  ajax: service('ajax'),

  actions: {
    submitFacility() {
      let isNew = typeof this.get('model.facility.id') === 'undefined';
      let method = isNew ? 'post' : 'put';
      let request = '/admin/' + (isNew ? 'createFacility' : 'editFacility');

      this.get('ajax')[method](request, {
        xhrFields: {
          withCredentials: true,
        },
        data: JSON.stringify(this.get('model.facility')),
      })
      .then(() => this.transitionToRoute('admin.facilities'));
    },
  },
});
