import { ThirdPartyAppName } from 'app/entities/enumerations/third-party-app-name.model';

export interface IThirdPartyApp {
  id?: number;
  name?: ThirdPartyAppName | null;
  baseUrl?: string | null;
  accessKey?: string | null;
  authorId?: string | null;
  creatingPostApi?: string | null;
  readPostApi?: string | null;
  active?: boolean | null;
}

export class ThirdPartyApp implements IThirdPartyApp {
  constructor(
    public id?: number,
    public name?: ThirdPartyAppName | null,
    public baseUrl?: string | null,
    public accessKey?: string | null,
    public authorId?: string | null,
    public creatingPostApi?: string | null,
    public readPostApi?: string | null,
    public active?: boolean | null
  ) {
    this.active = this.active ?? false;
  }
}

export function getThirdPartyAppIdentifier(thirdPartyApp: IThirdPartyApp): number | undefined {
  return thirdPartyApp.id;
}
