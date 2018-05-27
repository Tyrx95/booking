import AjaxService from 'ember-ajax/services/ajax';

export default AjaxService.extend({
  namespace: '/api/v1',
  contentType: 'application/json; charset=utf-8'
});
