import Ember from 'ember';

export function lodgingRoomEnum(index) {
  return parseInt(index) + 1;
}

export default Ember.Helper.helper(lodgingRoomEnum);
