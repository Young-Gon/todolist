export interface Question {
	no: number
	content?: string
	type: QuestionType
	objectiveItems?: []
}

export class QuestionImp implements Question {
	public content: string = "";
	public no: number = 0;
	public type: QuestionType = QuestionType.Subjective;
}

export enum QuestionType {
	Subjective= "Subjective",
	Objective= "Objective",
	LevelSelect= "LevelSelect",
	MultiChoice= "MultiChoice",
}
