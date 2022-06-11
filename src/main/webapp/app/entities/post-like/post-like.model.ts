import { IPost } from 'app/entities/post/post.model';

export interface IPostLike {
  id?: number;
  post?: IPost | null;
}

export class PostLike implements IPostLike {
  constructor(public id?: number, public post?: IPost | null) {}
}

export function getPostLikeIdentifier(postLike: IPostLike): number | undefined {
  return postLike.id;
}
