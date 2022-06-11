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

describe('ThirdPartyApp e2e test', () => {
  const thirdPartyAppPageUrl = '/third-party-app';
  const thirdPartyAppPageUrlPattern = new RegExp('/third-party-app(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const thirdPartyAppSample = {};

  let thirdPartyApp: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/third-party-apps+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/third-party-apps').as('postEntityRequest');
    cy.intercept('DELETE', '/api/third-party-apps/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (thirdPartyApp) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/third-party-apps/${thirdPartyApp.id}`,
      }).then(() => {
        thirdPartyApp = undefined;
      });
    }
  });

  it('ThirdPartyApps menu should load ThirdPartyApps page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('third-party-app');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('ThirdPartyApp').should('exist');
    cy.url().should('match', thirdPartyAppPageUrlPattern);
  });

  describe('ThirdPartyApp page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(thirdPartyAppPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create ThirdPartyApp page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/third-party-app/new$'));
        cy.getEntityCreateUpdateHeading('ThirdPartyApp');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', thirdPartyAppPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/third-party-apps',
          body: thirdPartyAppSample,
        }).then(({ body }) => {
          thirdPartyApp = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/third-party-apps+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/third-party-apps?page=0&size=20>; rel="last",<http://localhost/api/third-party-apps?page=0&size=20>; rel="first"',
              },
              body: [thirdPartyApp],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(thirdPartyAppPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details ThirdPartyApp page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('thirdPartyApp');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', thirdPartyAppPageUrlPattern);
      });

      it('edit button click should load edit ThirdPartyApp page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('ThirdPartyApp');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', thirdPartyAppPageUrlPattern);
      });

      it('last delete button click should delete instance of ThirdPartyApp', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('thirdPartyApp').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', thirdPartyAppPageUrlPattern);

        thirdPartyApp = undefined;
      });
    });
  });

  describe('new ThirdPartyApp page', () => {
    beforeEach(() => {
      cy.visit(`${thirdPartyAppPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('ThirdPartyApp');
    });

    it('should create an instance of ThirdPartyApp', () => {
      cy.get(`[data-cy="name"]`).select('MEDIUM');

      cy.get(`[data-cy="baseUrl"]`).type('definition Incredible index').should('have.value', 'definition Incredible index');

      cy.get(`[data-cy="accessKey"]`).type('Metal').should('have.value', 'Metal');

      cy.get(`[data-cy="authorId"]`).type('generating Architect').should('have.value', 'generating Architect');

      cy.get(`[data-cy="creatingPostApi"]`).type('Small Ohio Bedfordshire').should('have.value', 'Small Ohio Bedfordshire');

      cy.get(`[data-cy="readPostApi"]`).type('Account Pound').should('have.value', 'Account Pound');

      cy.get(`[data-cy="active"]`).should('not.be.checked');
      cy.get(`[data-cy="active"]`).click().should('be.checked');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        thirdPartyApp = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', thirdPartyAppPageUrlPattern);
    });
  });
});
