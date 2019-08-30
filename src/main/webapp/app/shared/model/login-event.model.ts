import { Moment } from 'moment';

export interface ILoginEvent {
  id?: number;
  username?: string;
  application?: string;
  loginDate?: Moment;
  ipAddress?: string;
  delegationId?: number;
  delegator?: string;
}

export class LoginEvent implements ILoginEvent {
  constructor(
    public id?: number,
    public username?: string,
    public application?: string,
    public loginDate?: Moment,
    public ipAddress?: string,
    public delegationId?: number,
    public delegator?: string
  ) {}
}
