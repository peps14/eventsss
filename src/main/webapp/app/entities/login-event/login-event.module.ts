import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EventsssSharedModule } from 'app/shared';
import {
  LoginEventComponent,
  LoginEventDetailComponent,
  LoginEventUpdateComponent,
  LoginEventDeletePopupComponent,
  LoginEventDeleteDialogComponent,
  loginEventRoute,
  loginEventPopupRoute
} from './';

const ENTITY_STATES = [...loginEventRoute, ...loginEventPopupRoute];

@NgModule({
  imports: [EventsssSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    LoginEventComponent,
    LoginEventDetailComponent,
    LoginEventUpdateComponent,
    LoginEventDeleteDialogComponent,
    LoginEventDeletePopupComponent
  ],
  entryComponents: [LoginEventComponent, LoginEventUpdateComponent, LoginEventDeleteDialogComponent, LoginEventDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EventsssLoginEventModule {}
