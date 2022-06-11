import { entityItemSelector } from '../../support/commands';
import {
  entityTableSelector,
  entityDetailsButtonSelector,
  entityDetailsBackButtonSelector,
  entityCreateButtonSelector,
  entityCreateSaveButtonSelector,
  entityCreateCancelButtonSelector,
  entityEditButtonSelector,
  entityDeleteButtonSelector,
  entityConfirmDeleteButtonSelector,
} from '../../support/entity';

describe('PostView e2e test', () => {
  const postViewPageUrl = '/post-view';
  const postViewPageUrlPattern = new RegExp('/post-view(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const postViewSample = {};

  let postView: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/post-views+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/post-views').as('postEntityRequest');
    cy.intercept('DELETE', '/api/post-views/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (postView) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/post-views/${postView.id}`,
      }).then(() => {
        postView = undefined;
      });
    }
  });

  it('PostViews menu should load PostViews page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('post-view');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('PostView').should('exist');
    cy.url().should('match', postViewPageUrlPattern);
  });

  describe('PostView page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(postViewPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create PostView page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/post-view/new$'));
        cy.getEntityCreateUpdateHeading('PostView');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', postViewPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/post-views',
          body: postViewSample,
        }).then(({ body }) => {
          postView = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/post-views+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/post-views?page=0&size=20>; rel="last",<http://localhost/api/post-views?page=0&size=20>; rel="first"',
              },
              body: [postView],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(postViewPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details PostView page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('postView');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', postViewPageUrlPattern);
      });

      it('edit button click should load edit PostView page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('PostView');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', postViewPageUrlPattern);
      });

      it('last delete button click should delete instance of PostView', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('postView').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', postViewPageUrlPattern);

        postView = undefined;
      });
    });
  });

  describe('new PostView page', () => {
    beforeEach(() => {
      cy.visit(`${postViewPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('PostView');
    });

    it('should create an instance of PostView', () => {
      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        postView = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', postViewPageUrlPattern);
    });
  });
});
