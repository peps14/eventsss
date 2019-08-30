import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ILoginEvent } from 'app/shared/model/login-event.model';

type EntityResponseType = HttpResponse<ILoginEvent>;
type EntityArrayResponseType = HttpResponse<ILoginEvent[]>;

@Injectable({ providedIn: 'root' })
export class LoginEventService {
  public resourceUrl = SERVER_API_URL + 'api/login-events';

  constructor(protected http: HttpClient) {}

  create(loginEvent: ILoginEvent): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(loginEvent);
    return this.http
      .post<ILoginEvent>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(loginEvent: ILoginEvent): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(loginEvent);
    return this.http
      .put<ILoginEvent>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ILoginEvent>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ILoginEvent[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(loginEvent: ILoginEvent): ILoginEvent {
    const copy: ILoginEvent = Object.assign({}, loginEvent, {
      loginDate: loginEvent.loginDate != null && loginEvent.loginDate.isValid() ? loginEvent.loginDate.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.loginDate = res.body.loginDate != null ? moment(res.body.loginDate) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((loginEvent: ILoginEvent) => {
        loginEvent.loginDate = loginEvent.loginDate != null ? moment(loginEvent.loginDate) : null;
      });
    }
    return res;
  }
}
