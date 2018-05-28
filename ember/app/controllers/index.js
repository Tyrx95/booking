import Ember from 'ember';

const {
  inject: {
    service,
  },
  computed,
  computed: {
    notEmpty,
  },
} = Ember;

export default Ember.Controller.extend({
  ajax: service('ajax'),

  lodging_name: '',
  number_of_people: 2,

  hasPopularLodgings: notEmpty('model.popularLodgings'),
  geolocation: navigator.geolocation,
  date: new Date().toISOString().substring(0, 10),
  endDate: computed('date',function () {
    var date = this.get('date');
    date = ( date.constructor === String)
      ? this.stringToDate(date, 'yyyy-mm-dd','-') : date[0]; //TODO define new to Date function
    date.setDate(date.getDate()+1);
    return date.toISOString().substring(0, 10);
  }),
  todayDate:  new Date().toISOString().substring(0, 10),
  minEndDate: computed('date', function(){
    var date = this.get('date');
    date = ( date.constructor === String)
      ? this.stringToDate(date, 'yyyy-mm-dd','-') : date[0]; //TODO define new to Date function
    date.setDate(date.getDate()+1);
    return date.toISOString().substring(0, 10);
  }),

  stringToDate: function(_date,_format,_delimiter)
    {
      var formatLowerCase=_format.toLowerCase();
      var formatItems=formatLowerCase.split(_delimiter);
      var dateItems=_date.split(_delimiter);
      var monthIndex=formatItems.indexOf("mm");
      var dayIndex=formatItems.indexOf("dd");
      var yearIndex=formatItems.indexOf("yyyy");
      var month=parseInt(dateItems[monthIndex]);
      month-=1;
      var formatedDate = new Date(dateItems[yearIndex],month,dateItems[dayIndex]);
      formatedDate.setDate(formatedDate.getDate()+1);
      return formatedDate;
    },

  nearbyLodgings: function () {
    if (this.get('geolocation')) {
      this.get('geolocation').getCurrentPosition(
        (coordinates) => {
          this.get('ajax')
          .request('/getNearbyLodgings/' + coordinates.coords.latitude + '/' + coordinates.coords.longitude)
          .then(result => this.set('nearbyLodgings', result));
        }, () => this.set('geolocation', false)
      );
    }
  }.property('nearbyLodgings'),

  actions: {
    findRoom() {
      var date = this.get('date');
      var endDate = this.get('endDate');
      if(date.constructor !== String ){
        date = date[0].toISOString().substring(0, 10);
      }
      if(endDate.constructor !== String ){
        endDate = endDate[0].toISOString().substring(0, 10);
      }
      let filters = {
        name: this.get('lodging_name'),
        people: this.get('number_of_people'),
        date: date,
        endDate: endDate
      };
      this.transitionToRoute('search-results', { queryParams: filters });
    },

    setNumberOfPeople() {
      let selectBox = document.getElementById('numberOfPeople');
      this.set('number_of_people', selectBox.options[selectBox.selectedIndex].value);
    },
  },

});
