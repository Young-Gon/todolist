export interface User {
	id: bigint
	name: string
	email: string
	provider?: string
	imageUrl?: string
}

export class UserImp implements User {
	public id: bigint = BigInt(0);
	public name: string = "";
	public email: string = "";
	public provider?: string;
	public imageUrl?: string
}
