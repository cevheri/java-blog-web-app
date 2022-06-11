import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PostViewDetailComponent } from './post-view-detail.component';

describe('PostView Management Detail Component', () => {
  let comp: PostViewDetailComponent;
  let fixture: ComponentFixture<PostViewDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PostViewDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ postView: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(PostViewDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(PostViewDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load postView on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.postView).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
