import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { LoginEvent } from 'app/shared/model/login-event.model';
import { LoginEventService } from './login-event.service';
import { LoginEventComponent } from './login-event.component';
import { LoginEventDetailComponent } from './login-event-detail.component';
import { LoginEventUpdateComponent } from './login-event-update.component';
import { LoginEventDeletePopupComponent } from './login-event-delete-dialog.component';
import { ILoginEvent } from 'app/shared/model/login-event.model';

@Injectable({ providedIn: 'root' })
export class LoginEventResolve implements Resolve<ILoginEvent> {
  constructor(private service: LoginEventService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ILoginEvent> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<LoginEvent>) => response.ok),
        map((loginEvent: HttpResponse<LoginEvent>) => loginEvent.body)
      );
    }
    return of(new LoginEvent());
  }
}

export const loginEventRoute: Routes = [
  {
    path: '',
    component: LoginEventComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'LoginEvents'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: LoginEventDetailComponent,
    resolve: {
      loginEvent: LoginEventResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'LoginEvents'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: LoginEventUpdateComponent,
    resolve: {
      loginEvent: LoginEventResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'LoginEvents'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: LoginEventUpdateComponent,
    resolve: {
      loginEvent: LoginEventResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'LoginEvents'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const loginEventPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: LoginEventDeletePopupComponent,
    resolve: {
      loginEvent: LoginEventResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'LoginEvents'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
