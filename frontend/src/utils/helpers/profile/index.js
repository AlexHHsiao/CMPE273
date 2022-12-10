import {userFormDataTemplate} from "../../constants/profile";

export const formatData = (data) =>
  userFormDataTemplate.map((val) => {
    const {name} = val;
    val.value = data[name];
    return val;
  });
