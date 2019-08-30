/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { EventsssTestModule } from '../../../test.module';
import { LoginEventUpdateComponent } from 'app/entities/login-event/login-event-update.component';
import { LoginEventService } from 'app/entities/login-event/login-event.service';
import { LoginEvent } from 'app/shared/model/login-event.model';

describe('Component Tests', () => {
  describe('LoginEvent Management Update Component', () => {
    let comp: LoginEventUpdateComponent;
    let fixture: ComponentFixture<LoginEventUpdateComponent>;
    let service: LoginEventService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EventsssTestModule],
        declarations: [LoginEventUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(LoginEventUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LoginEventUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LoginEventService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new LoginEvent(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new LoginEvent();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
