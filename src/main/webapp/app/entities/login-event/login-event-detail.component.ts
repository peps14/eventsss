import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILoginEvent } from 'app/shared/model/login-event.model';

@Component({
  selector: 'jhi-login-event-detail',
  templateUrl: './login-event-detail.component.html'
})
export class LoginEventDetailComponent implements OnInit {
  loginEvent: ILoginEvent;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ loginEvent }) => {
      this.loginEvent = loginEvent;
    });
  }

  previousState() {
    window.history.back();
  }
}
