/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { EventsssTestModule } from '../../../test.module';
import { LoginEventDeleteDialogComponent } from 'app/entities/login-event/login-event-delete-dialog.component';
import { LoginEventService } from 'app/entities/login-event/login-event.service';

describe('Component Tests', () => {
  describe('LoginEvent Management Delete Component', () => {
    let comp: LoginEventDeleteDialogComponent;
    let fixture: ComponentFixture<LoginEventDeleteDialogComponent>;
    let service: LoginEventService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EventsssTestModule],
        declarations: [LoginEventDeleteDialogComponent]
      })
        .overrideTemplate(LoginEventDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(LoginEventDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LoginEventService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
