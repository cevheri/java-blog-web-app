import { IUser } from 'app/entities/user/user.model';

export interface IBlog {
  id?: number;
  name?: string;
  user?: IUser | null;
}

export class Blog implements IBlog {
  constructor(public id?: number, public name?: string, public user?: IUser | null) {}
}

export function getBlogIdentifier(blog: IBlog): number | undefined {
  return blog.id;
}
