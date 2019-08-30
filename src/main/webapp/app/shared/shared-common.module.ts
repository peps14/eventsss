import { NgModule } from '@angular/core';

import { EventsssSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
  imports: [EventsssSharedLibsModule],
  declarations: [JhiAlertComponent, JhiAlertErrorComponent],
  exports: [EventsssSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class EventsssSharedCommonModule {}
