import Ember from 'ember';
import EmberUploader from 'ember-uploader';

const {
  isEmpty,
} = Ember;

export default EmberUploader.FileField.extend({
  filesDidChange(files) {
    const uploader = EmberUploader.Uploader.create({
      url: this.get('url'),
    });

    if (!isEmpty(files)) {
      // this second argument is optional and can to be sent as extra data with the upload
        let timestamp = Date.now();
        this.set('timestamp', timestamp);
        uploader.upload(files[0], {timestamp: timestamp});
    }

    uploader.on('progress', (e) => {
      this.set('progress', Math.round(e.percent) - 1);
    });

    uploader.on('didUpload', (response) => {
      console.log(response);
      console.log(this.get('model'));
      this.set('progress', null);
      let explodedFilename = files[0].name.split('.');
      if(this.get('model.isEdit')){
        this.sendAction('onFinishedUpload', this.get('imageFor'), response.path);
      }
      else{
        if(this.get('imageFor') === 'gallery' ){
          this.get('model.lodging.photos').pushObject(
            {
              imageType: this.get('imageFor'),
              explodedFilename: explodedFilename[explodedFilename.length - 1],
              timestamp: this.get('timestamp'),
              path: response.path,
              extension: explodedFilename[explodedFilename.length - 1],
              lodgingId: ''
            })
        }
        else if(this.get('imageFor') === 'cover'){
          this.set("model.lodging.coverImagePath",response.path);
        }
        else{
          this.set("model.lodging.profileImagePath",response.path);
        }
      }
    });
  },
});
