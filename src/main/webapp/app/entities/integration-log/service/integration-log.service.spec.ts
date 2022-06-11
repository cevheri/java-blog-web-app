import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ThirdPartyAppName } from 'app/entities/enumerations/third-party-app-name.model';
import { ExitCodeType } from 'app/entities/enumerations/exit-code-type.model';
import { IIntegrationLog, IntegrationLog } from '../integration-log.model';

import { IntegrationLogService } from './integration-log.service';

describe('IntegrationLog Service', () => {
  let service: IntegrationLogService;
  let httpMock: HttpTestingController;
  let elemDefault: IIntegrationLog;
  let expectedResult: IIntegrationLog | IIntegrationLog[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(IntegrationLogService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      createdBy: 'AAAAAAA',
      createdDate: currentDate,
      integrationName: ThirdPartyAppName.MEDIUM,
      apiUrl: 'AAAAAAA',
      exitCode: ExitCodeType.SUCCESS,
      requestData: 'AAAAAAA',
      responseData: 'AAAAAAA',
      errorCode: 'AAAAAAA',
      errorMessage: 'AAAAAAA',
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          createdDate: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a IntegrationLog', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          createdDate: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          createdDate: currentDate,
        },
        returnedFromService
      );

      service.create(new IntegrationLog()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a IntegrationLog', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          createdBy: 'BBBBBB',
          createdDate: currentDate.format(DATE_TIME_FORMAT),
          integrationName: 'BBBBBB',
          apiUrl: 'BBBBBB',
          exitCode: 'BBBBBB',
          requestData: 'BBBBBB',
          responseData: 'BBBBBB',
          errorCode: 'BBBBBB',
          errorMessage: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          createdDate: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a IntegrationLog', () => {
      const patchObject = Object.assign(
        {
          createdDate: currentDate.format(DATE_TIME_FORMAT),
          integrationName: 'BBBBBB',
          responseData: 'BBBBBB',
          errorMessage: 'BBBBBB',
        },
        new IntegrationLog()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          createdDate: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of IntegrationLog', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          createdBy: 'BBBBBB',
          createdDate: currentDate.format(DATE_TIME_FORMAT),
          integrationName: 'BBBBBB',
          apiUrl: 'BBBBBB',
          exitCode: 'BBBBBB',
          requestData: 'BBBBBB',
          responseData: 'BBBBBB',
          errorCode: 'BBBBBB',
          errorMessage: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          createdDate: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a IntegrationLog', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addIntegrationLogToCollectionIfMissing', () => {
      it('should add a IntegrationLog to an empty array', () => {
        const integrationLog: IIntegrationLog = { id: 123 };
        expectedResult = service.addIntegrationLogToCollectionIfMissing([], integrationLog);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(integrationLog);
      });

      it('should not add a IntegrationLog to an array that contains it', () => {
        const integrationLog: IIntegrationLog = { id: 123 };
        const integrationLogCollection: IIntegrationLog[] = [
          {
            ...integrationLog,
          },
          { id: 456 },
        ];
        expectedResult = service.addIntegrationLogToCollectionIfMissing(integrationLogCollection, integrationLog);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a IntegrationLog to an array that doesn't contain it", () => {
        const integrationLog: IIntegrationLog = { id: 123 };
        const integrationLogCollection: IIntegrationLog[] = [{ id: 456 }];
        expectedResult = service.addIntegrationLogToCollectionIfMissing(integrationLogCollection, integrationLog);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(integrationLog);
      });

      it('should add only unique IntegrationLog to an array', () => {
        const integrationLogArray: IIntegrationLog[] = [{ id: 123 }, { id: 456 }, { id: 65314 }];
        const integrationLogCollection: IIntegrationLog[] = [{ id: 123 }];
        expectedResult = service.addIntegrationLogToCollectionIfMissing(integrationLogCollection, ...integrationLogArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const integrationLog: IIntegrationLog = { id: 123 };
        const integrationLog2: IIntegrationLog = { id: 456 };
        expectedResult = service.addIntegrationLogToCollectionIfMissing([], integrationLog, integrationLog2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(integrationLog);
        expect(expectedResult).toContain(integrationLog2);
      });

      it('should accept null and undefined values', () => {
        const integrationLog: IIntegrationLog = { id: 123 };
        expectedResult = service.addIntegrationLogToCollectionIfMissing([], null, integrationLog, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(integrationLog);
      });

      it('should return initial array if no IntegrationLog is added', () => {
        const integrationLogCollection: IIntegrationLog[] = [{ id: 123 }];
        expectedResult = service.addIntegrationLogToCollectionIfMissing(integrationLogCollection, undefined, null);
        expect(expectedResult).toEqual(integrationLogCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
