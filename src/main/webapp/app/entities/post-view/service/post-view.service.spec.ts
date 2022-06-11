import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IPostView, PostView } from '../post-view.model';

import { PostViewService } from './post-view.service';

describe('PostView Service', () => {
  let service: PostViewService;
  let httpMock: HttpTestingController;
  let elemDefault: IPostView;
  let expectedResult: IPostView | IPostView[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PostViewService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
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

    it('should create a PostView', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new PostView()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a PostView', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a PostView', () => {
      const patchObject = Object.assign({}, new PostView());

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of PostView', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
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

    it('should delete a PostView', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addPostViewToCollectionIfMissing', () => {
      it('should add a PostView to an empty array', () => {
        const postView: IPostView = { id: 123 };
        expectedResult = service.addPostViewToCollectionIfMissing([], postView);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(postView);
      });

      it('should not add a PostView to an array that contains it', () => {
        const postView: IPostView = { id: 123 };
        const postViewCollection: IPostView[] = [
          {
            ...postView,
          },
          { id: 456 },
        ];
        expectedResult = service.addPostViewToCollectionIfMissing(postViewCollection, postView);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a PostView to an array that doesn't contain it", () => {
        const postView: IPostView = { id: 123 };
        const postViewCollection: IPostView[] = [{ id: 456 }];
        expectedResult = service.addPostViewToCollectionIfMissing(postViewCollection, postView);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(postView);
      });

      it('should add only unique PostView to an array', () => {
        const postViewArray: IPostView[] = [{ id: 123 }, { id: 456 }, { id: 5742 }];
        const postViewCollection: IPostView[] = [{ id: 123 }];
        expectedResult = service.addPostViewToCollectionIfMissing(postViewCollection, ...postViewArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const postView: IPostView = { id: 123 };
        const postView2: IPostView = { id: 456 };
        expectedResult = service.addPostViewToCollectionIfMissing([], postView, postView2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(postView);
        expect(expectedResult).toContain(postView2);
      });

      it('should accept null and undefined values', () => {
        const postView: IPostView = { id: 123 };
        expectedResult = service.addPostViewToCollectionIfMissing([], null, postView, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(postView);
      });

      it('should return initial array if no PostView is added', () => {
        const postViewCollection: IPostView[] = [{ id: 123 }];
        expectedResult = service.addPostViewToCollectionIfMissing(postViewCollection, undefined, null);
        expect(expectedResult).toEqual(postViewCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
