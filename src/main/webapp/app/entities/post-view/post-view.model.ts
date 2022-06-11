import { IPost } from 'app/entities/post/post.model';

export interface IPostView {
  id?: number;
  post?: IPost | null;
}

export class PostView implements IPostView {
  constructor(public id?: number, public post?: IPost | null) {}
}

export function getPostViewIdentifier(postView: IPostView): number | undefined {
  return postView.id;
}
