import {AUTHORIZATION} from "../../constants/session-storage";

export const getAuthToken = () => sessionStorage.getItem(AUTHORIZATION);
export const setAuthToken = (autoToken) =>
  sessionStorage.setItem(AUTHORIZATION, `${autoToken}`);
