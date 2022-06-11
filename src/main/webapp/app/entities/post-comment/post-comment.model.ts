import { IPost } from 'app/entities/post/post.model';

export interface IPostComment {
  id?: number;
  commentText?: string;
  post?: IPost | null;
}

export class PostComment implements IPostComment {
  constructor(public id?: number, public commentText?: string, public post?: IPost | null) {}
}

export function getPostCommentIdentifier(postComment: IPostComment): number | undefined {
  return postComment.id;
}
