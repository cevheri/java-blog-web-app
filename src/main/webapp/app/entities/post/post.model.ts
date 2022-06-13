import { IUser } from 'app/entities/user/user.model';
import { IBlog } from 'app/entities/blog/blog.model';
import { ITag } from 'app/entities/tag/tag.model';

export interface IPost {
  id?: number;
  title?: string;
  content?: string;
  paidMemberOnly?: boolean | null;
  publishThirdPartyApp?: boolean | null;
  user?: IUser | null;
  blog?: IBlog | null;
  tags?: ITag[] | null;
  integrationId?: string;
}

export class Post implements IPost {
  constructor(
    public id?: number,
    public title?: string,
    public content?: string,
    public paidMemberOnly?: boolean | null,
    public publishThirdPartyApp?: boolean | null,
    public user?: IUser | null,
    public blog?: IBlog | null,
    public tags?: ITag[] | null,
    public integrationId?: string
  ) {
    this.paidMemberOnly = this.paidMemberOnly ?? false;
    this.publishThirdPartyApp = this.publishThirdPartyApp ?? false;
  }
}

export function getPostIdentifier(post: IPost): number | undefined {
  return post.id;
}
