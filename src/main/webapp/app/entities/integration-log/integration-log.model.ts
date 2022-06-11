import dayjs from 'dayjs/esm';
import { ThirdPartyAppName } from 'app/entities/enumerations/third-party-app-name.model';
import { ExitCodeType } from 'app/entities/enumerations/exit-code-type.model';

export interface IIntegrationLog {
  id?: number;
  createdBy?: string | null;
  createdDate?: dayjs.Dayjs | null;
  integrationName?: ThirdPartyAppName | null;
  apiUrl?: string | null;
  exitCode?: ExitCodeType | null;
  requestData?: string | null;
  responseData?: string | null;
  errorCode?: string | null;
  errorMessage?: string | null;
}

export class IntegrationLog implements IIntegrationLog {
  constructor(
    public id?: number,
    public createdBy?: string | null,
    public createdDate?: dayjs.Dayjs | null,
    public integrationName?: ThirdPartyAppName | null,
    public apiUrl?: string | null,
    public exitCode?: ExitCodeType | null,
    public requestData?: string | null,
    public responseData?: string | null,
    public errorCode?: string | null,
    public errorMessage?: string | null
  ) {}
}

export function getIntegrationLogIdentifier(integrationLog: IIntegrationLog): number | undefined {
  return integrationLog.id;
}
