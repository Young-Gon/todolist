import {User, UserImp} from "@/response/user";
import {Question} from "@/response/question";

export interface Survey {
	title: string;
	description?: string;
	questions?: Question[];
	id: number;
	createAt: string;
	modifyAt: string;
	user: User
}

export class SurveyImp implements Survey {
	public title: string = "";
	public description?: string;
	public id: number = 0;
	public createAt: string = "";
	public modifyAt: string = "";
	public user: User = new UserImp();
}
