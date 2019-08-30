/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EventsssTestModule } from '../../../test.module';
import { LoginEventDetailComponent } from 'app/entities/login-event/login-event-detail.component';
import { LoginEvent } from 'app/shared/model/login-event.model';

describe('Component Tests', () => {
  describe('LoginEvent Management Detail Component', () => {
    let comp: LoginEventDetailComponent;
    let fixture: ComponentFixture<LoginEventDetailComponent>;
    const route = ({ data: of({ loginEvent: new LoginEvent(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EventsssTestModule],
        declarations: [LoginEventDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(LoginEventDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(LoginEventDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.loginEvent).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
