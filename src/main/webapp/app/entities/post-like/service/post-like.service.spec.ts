import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IPostLike, PostLike } from '../post-like.model';

import { PostLikeService } from './post-like.service';

describe('PostLike Service', () => {
  let service: PostLikeService;
  let httpMock: HttpTestingController;
  let elemDefault: IPostLike;
  let expectedResult: IPostLike | IPostLike[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PostLikeService);
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

    it('should create a PostLike', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new PostLike()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a PostLike', () => {
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

    it('should partial update a PostLike', () => {
      const patchObject = Object.assign({}, new PostLike());

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of PostLike', () => {
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

    it('should delete a PostLike', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addPostLikeToCollectionIfMissing', () => {
      it('should add a PostLike to an empty array', () => {
        const postLike: IPostLike = { id: 123 };
        expectedResult = service.addPostLikeToCollectionIfMissing([], postLike);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(postLike);
      });

      it('should not add a PostLike to an array that contains it', () => {
        const postLike: IPostLike = { id: 123 };
        const postLikeCollection: IPostLike[] = [
          {
            ...postLike,
          },
          { id: 456 },
        ];
        expectedResult = service.addPostLikeToCollectionIfMissing(postLikeCollection, postLike);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a PostLike to an array that doesn't contain it", () => {
        const postLike: IPostLike = { id: 123 };
        const postLikeCollection: IPostLike[] = [{ id: 456 }];
        expectedResult = service.addPostLikeToCollectionIfMissing(postLikeCollection, postLike);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(postLike);
      });

      it('should add only unique PostLike to an array', () => {
        const postLikeArray: IPostLike[] = [{ id: 123 }, { id: 456 }, { id: 79717 }];
        const postLikeCollection: IPostLike[] = [{ id: 123 }];
        expectedResult = service.addPostLikeToCollectionIfMissing(postLikeCollection, ...postLikeArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const postLike: IPostLike = { id: 123 };
        const postLike2: IPostLike = { id: 456 };
        expectedResult = service.addPostLikeToCollectionIfMissing([], postLike, postLike2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(postLike);
        expect(expectedResult).toContain(postLike2);
      });

      it('should accept null and undefined values', () => {
        const postLike: IPostLike = { id: 123 };
        expectedResult = service.addPostLikeToCollectionIfMissing([], null, postLike, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(postLike);
      });

      it('should return initial array if no PostLike is added', () => {
        const postLikeCollection: IPostLike[] = [{ id: 123 }];
        expectedResult = service.addPostLikeToCollectionIfMissing(postLikeCollection, undefined, null);
        expect(expectedResult).toEqual(postLikeCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
