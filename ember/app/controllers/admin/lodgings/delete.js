import Ember from 'ember';

const {
  inject: {
    service,
  },
} = Ember;

export default Ember.Controller.extend({
  ajax: service('ajax'),

  actions: {
    deleteLodging() {
      this.get('ajax').del('/admin/deleteLodging/' + this.get('model.lodging.id'), {
        xhrFields: {
          withCredentials: true,
        },
      })
      .then(
        () => this.transitionToRoute('admin.lodgings'),
        (error) =>  alert(error)
      );
    },

    cancel() {
      this.transitionToRoute('admin.lodgings');
    },
  },
});
