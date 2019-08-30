import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ILoginEvent } from 'app/shared/model/login-event.model';
import { LoginEventService } from './login-event.service';

@Component({
  selector: 'jhi-login-event-delete-dialog',
  templateUrl: './login-event-delete-dialog.component.html'
})
export class LoginEventDeleteDialogComponent {
  loginEvent: ILoginEvent;

  constructor(
    protected loginEventService: LoginEventService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.loginEventService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'loginEventListModification',
        content: 'Deleted an loginEvent'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-login-event-delete-popup',
  template: ''
})
export class LoginEventDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ loginEvent }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(LoginEventDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.loginEvent = loginEvent;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/login-event', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/login-event', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
