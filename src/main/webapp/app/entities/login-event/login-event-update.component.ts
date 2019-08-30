import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { ILoginEvent, LoginEvent } from 'app/shared/model/login-event.model';
import { LoginEventService } from './login-event.service';

@Component({
  selector: 'jhi-login-event-update',
  templateUrl: './login-event-update.component.html'
})
export class LoginEventUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    username: [null, [Validators.required]],
    application: [null, [Validators.required]],
    loginDate: [null, [Validators.required]],
    ipAddress: [null, [Validators.required]],
    delegationId: [],
    delegator: []
  });

  constructor(protected loginEventService: LoginEventService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ loginEvent }) => {
      this.updateForm(loginEvent);
    });
  }

  updateForm(loginEvent: ILoginEvent) {
    this.editForm.patchValue({
      id: loginEvent.id,
      username: loginEvent.username,
      application: loginEvent.application,
      loginDate: loginEvent.loginDate != null ? loginEvent.loginDate.format(DATE_TIME_FORMAT) : null,
      ipAddress: loginEvent.ipAddress,
      delegationId: loginEvent.delegationId,
      delegator: loginEvent.delegator
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const loginEvent = this.createFromForm();
    if (loginEvent.id !== undefined) {
      this.subscribeToSaveResponse(this.loginEventService.update(loginEvent));
    } else {
      this.subscribeToSaveResponse(this.loginEventService.create(loginEvent));
    }
  }

  private createFromForm(): ILoginEvent {
    return {
      ...new LoginEvent(),
      id: this.editForm.get(['id']).value,
      username: this.editForm.get(['username']).value,
      application: this.editForm.get(['application']).value,
      loginDate:
        this.editForm.get(['loginDate']).value != null ? moment(this.editForm.get(['loginDate']).value, DATE_TIME_FORMAT) : undefined,
      ipAddress: this.editForm.get(['ipAddress']).value,
      delegationId: this.editForm.get(['delegationId']).value,
      delegator: this.editForm.get(['delegator']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILoginEvent>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
