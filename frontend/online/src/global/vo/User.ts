class User{
    _uuid = '';
    _userAccount = '';
    _userPassword = '';
    _userName = 'momo';
    _userSex = 0;
    _userAge = 0;
    _userAvatar = '';
    _userEmail = '';
    _userPrivilege = 0;
    private _userSignature = '';

    get uuid(): string {
        return this._uuid;
    }

    set uuid(value: string) {
        this._uuid = value;
    }

    get userAccount(): string {
        return this._userAccount;
    }

    set userAccount(value: string) {
        this._userAccount = value;
    }

    get userPassword(): string {
        return this._userPassword;
    }

    set userPassword(value: string) {
        this._userPassword = value;
    }

    get userName(): string {
        return this._userName;
    }

    set userName(value: string) {
        this._userName = value;
    }

    get userSex(): number {
        return this._userSex;
    }

    set userSex(value: number) {
        this._userSex = value;
    }

    get userAge(): number {
        return this._userAge;
    }

    set userAge(value: number) {
        this._userAge = value;
    }

    get userAvatar(): string {
        return this._userAvatar;
    }

    set userAvatar(value: string) {
        this._userAvatar = value;
    }

    get userEmail(): string {
        return this._userEmail;
    }

    set userEmail(value: string) {
        this._userEmail = value;
    }

    get userPrivilege(): number {
        return this._userPrivilege;
    }

    set userPrivilege(value: number) {
        this._userPrivilege = value;
    }


    get userSignature(): string {
        return this._userSignature;
    }

    set userSignature(value: string) {
        this._userSignature = value;
    }
}

export default User;