import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ThirdPartyAppDetailComponent } from './third-party-app-detail.component';

describe('ThirdPartyApp Management Detail Component', () => {
  let comp: ThirdPartyAppDetailComponent;
  let fixture: ComponentFixture<ThirdPartyAppDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ThirdPartyAppDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ thirdPartyApp: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ThirdPartyAppDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ThirdPartyAppDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load thirdPartyApp on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.thirdPartyApp).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
