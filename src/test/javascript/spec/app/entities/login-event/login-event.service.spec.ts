/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { LoginEventService } from 'app/entities/login-event/login-event.service';
import { ILoginEvent, LoginEvent } from 'app/shared/model/login-event.model';

describe('Service Tests', () => {
  describe('LoginEvent Service', () => {
    let injector: TestBed;
    let service: LoginEventService;
    let httpMock: HttpTestingController;
    let elemDefault: ILoginEvent;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(LoginEventService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new LoginEvent(0, 'AAAAAAA', 'AAAAAAA', currentDate, 'AAAAAAA', 0, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            loginDate: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: elemDefault });
      });

      it('should create a LoginEvent', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            loginDate: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            loginDate: currentDate
          },
          returnedFromService
        );
        service
          .create(new LoginEvent(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a LoginEvent', async () => {
        const returnedFromService = Object.assign(
          {
            username: 'BBBBBB',
            application: 'BBBBBB',
            loginDate: currentDate.format(DATE_TIME_FORMAT),
            ipAddress: 'BBBBBB',
            delegationId: 1,
            delegator: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            loginDate: currentDate
          },
          returnedFromService
        );
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should return a list of LoginEvent', async () => {
        const returnedFromService = Object.assign(
          {
            username: 'BBBBBB',
            application: 'BBBBBB',
            loginDate: currentDate.format(DATE_TIME_FORMAT),
            ipAddress: 'BBBBBB',
            delegationId: 1,
            delegator: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            loginDate: currentDate
          },
          returnedFromService
        );
        service
          .query(expected)
          .pipe(
            take(1),
            map(resp => resp.body)
          )
          .subscribe(body => (expectedResult = body));
        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a LoginEvent', async () => {
        const rxPromise = service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
