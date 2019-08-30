import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { EventsssSharedCommonModule, HasAnyAuthorityDirective } from './';

@NgModule({
  imports: [EventsssSharedCommonModule],
  declarations: [HasAnyAuthorityDirective],
  exports: [EventsssSharedCommonModule, HasAnyAuthorityDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EventsssSharedModule {
  static forRoot() {
    return {
      ngModule: EventsssSharedModule
    };
  }
}
