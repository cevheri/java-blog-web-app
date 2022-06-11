import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ThirdPartyAppName } from 'app/entities/enumerations/third-party-app-name.model';
import { IThirdPartyApp, ThirdPartyApp } from '../third-party-app.model';

import { ThirdPartyAppService } from './third-party-app.service';

describe('ThirdPartyApp Service', () => {
  let service: ThirdPartyAppService;
  let httpMock: HttpTestingController;
  let elemDefault: IThirdPartyApp;
  let expectedResult: IThirdPartyApp | IThirdPartyApp[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ThirdPartyAppService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      name: ThirdPartyAppName.MEDIUM,
      baseUrl: 'AAAAAAA',
      accessKey: 'AAAAAAA',
      authorId: 'AAAAAAA',
      creatingPostApi: 'AAAAAAA',
      readPostApi: 'AAAAAAA',
      active: false,
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign({}, elemDefault);

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a ThirdPartyApp', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new ThirdPartyApp()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ThirdPartyApp', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          name: 'BBBBBB',
          baseUrl: 'BBBBBB',
          accessKey: 'BBBBBB',
          authorId: 'BBBBBB',
          creatingPostApi: 'BBBBBB',
          readPostApi: 'BBBBBB',
          active: true,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ThirdPartyApp', () => {
      const patchObject = Object.assign(
        {
          name: 'BBBBBB',
          accessKey: 'BBBBBB',
          creatingPostApi: 'BBBBBB',
          readPostApi: 'BBBBBB',
        },
        new ThirdPartyApp()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ThirdPartyApp', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          name: 'BBBBBB',
          baseUrl: 'BBBBBB',
          accessKey: 'BBBBBB',
          authorId: 'BBBBBB',
          creatingPostApi: 'BBBBBB',
          readPostApi: 'BBBBBB',
          active: true,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a ThirdPartyApp', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addThirdPartyAppToCollectionIfMissing', () => {
      it('should add a ThirdPartyApp to an empty array', () => {
        const thirdPartyApp: IThirdPartyApp = { id: 123 };
        expectedResult = service.addThirdPartyAppToCollectionIfMissing([], thirdPartyApp);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(thirdPartyApp);
      });

      it('should not add a ThirdPartyApp to an array that contains it', () => {
        const thirdPartyApp: IThirdPartyApp = { id: 123 };
        const thirdPartyAppCollection: IThirdPartyApp[] = [
          {
            ...thirdPartyApp,
          },
          { id: 456 },
        ];
        expectedResult = service.addThirdPartyAppToCollectionIfMissing(thirdPartyAppCollection, thirdPartyApp);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ThirdPartyApp to an array that doesn't contain it", () => {
        const thirdPartyApp: IThirdPartyApp = { id: 123 };
        const thirdPartyAppCollection: IThirdPartyApp[] = [{ id: 456 }];
        expectedResult = service.addThirdPartyAppToCollectionIfMissing(thirdPartyAppCollection, thirdPartyApp);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(thirdPartyApp);
      });

      it('should add only unique ThirdPartyApp to an array', () => {
        const thirdPartyAppArray: IThirdPartyApp[] = [{ id: 123 }, { id: 456 }, { id: 73730 }];
        const thirdPartyAppCollection: IThirdPartyApp[] = [{ id: 123 }];
        expectedResult = service.addThirdPartyAppToCollectionIfMissing(thirdPartyAppCollection, ...thirdPartyAppArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const thirdPartyApp: IThirdPartyApp = { id: 123 };
        const thirdPartyApp2: IThirdPartyApp = { id: 456 };
        expectedResult = service.addThirdPartyAppToCollectionIfMissing([], thirdPartyApp, thirdPartyApp2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(thirdPartyApp);
        expect(expectedResult).toContain(thirdPartyApp2);
      });

      it('should accept null and undefined values', () => {
        const thirdPartyApp: IThirdPartyApp = { id: 123 };
        expectedResult = service.addThirdPartyAppToCollectionIfMissing([], null, thirdPartyApp, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(thirdPartyApp);
      });

      it('should return initial array if no ThirdPartyApp is added', () => {
        const thirdPartyAppCollection: IThirdPartyApp[] = [{ id: 123 }];
        expectedResult = service.addThirdPartyAppToCollectionIfMissing(thirdPartyAppCollection, undefined, null);
        expect(expectedResult).toEqual(thirdPartyAppCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
